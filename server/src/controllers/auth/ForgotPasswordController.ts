import { Request, Response } from "express"
import { getConnection } from "typeorm"
import { ResetToken } from "../../entity/ResetToken"
import { User } from "../../entity/User"
import nodemailer from "nodemailer"
import dayjs from "dayjs"

export const forgotPassword = async(req: Request, res: Response) => {

   
    const { email } = req.body
    console.log("email", email)
    const currentUser = await User.findOne({ email })

    if(!currentUser) return res.status(401).json({message: "Email doesn't exist."})


    const testAccount = await nodemailer.createTestAccount()

    const transporter = nodemailer.createTransport({
        host: "smtp.ethereal.email",
        port: 587,
        secure: false,
        auth: {
            user: testAccount.user, // generated ethereal user
            pass: testAccount.pass, // generated ethereal password
        }
    })



    await getConnection()
        .createQueryBuilder()
        .update(ResetToken)
        .set({
            used: true
        })
        .where("email = :email", { email })
        .execute()

    var token = Math.floor(Math.random() * 99999) + 111111;


    var now = dayjs()
    console.log("now: ", now)
    var expireAt = now.add(2, "minute")
    console.log("expire:" , expireAt)

    await ResetToken.create({
        email,
        token: token.toString(),
        expireAt,
        used: false
    }).save()

    const data = {
        from: "Excited User <me@samples.mailgun.org>",
        to: "spoiden.benjamin@gmail.com",
        subject: "Reset Your Password",
        text: `Please enter the 6-digit code in order to reset your password. ${token}`
    }

    const mailed = await transporter.sendMail({...data}).catch(e => console.log(e))
    console.log("preview: ", nodemailer.getTestMessageUrl(mailed))
   

    return res.status(201).json({message: "OK"})
}