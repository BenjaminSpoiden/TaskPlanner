import { Request, Response } from "express"
import { getConnection } from "typeorm"
import { ResetToken } from "../../entity/ResetToken"
import { User } from "../../entity/User"
import mailgun from "mailgun-js"
import nodemailer from "nodemailer"


export const forgotPassword = async(req: Request, res: Response) => {

    //@ts-ignore
    var mailGun = mailgun({ apiKey: process.env.MAILGUN_API_KEY, domain: process.env.MAILGUN_DOMAIN })

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

    const { email } = req.body
    const currentUser = User.findOne({ email })

    if(!currentUser) return res.status(201).json({message: "OK"})

    await getConnection()
        .createQueryBuilder()
        .update(ResetToken)
        .set({
            used: true
        })
        .where("email = :email", { email })
        .execute()

    var token = Math.floor(Math.random() * 999999) + 10000;

    var expireAt = new Date()
    expireAt.setDate(expireAt.getDate() + 1 / 24)

    await ResetToken.create({
        email,
        token,
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