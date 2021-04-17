import { Request, Response } from "express"
import { ResetToken } from "../../entity/ResetToken"
import { getConnection } from "typeorm"
import argon2 from "argon2"
import { User } from "../../entity/User"


export const resetPassword = async(req: Request, res: Response) => {

    const { token, email } = req.query
    const { password, confirmPassword } = req.body

    if(password !== confirmPassword) return res.status(403).json({ message: "The passwords did not match." })

    var date = new Date()
    date.setDate(date.getDate())


    //Delete expired tokens
    await getConnection()
        .createQueryBuilder()
        .delete()
        .from(ResetToken)
        .where("expire_at > :date", { date })
        .execute()

    var record = await ResetToken.findOne({where: {
        email,
        token,
        used: false
    }})

    if(!record) return res.status(401).json({ message: "The token has expired." })

    //Set token to expired for deletion
    await getConnection()
        .createQueryBuilder()
        .update(ResetToken)
        .set({ used: true })
        .where("email = :email", { email })
        .execute()
        
    const newPassword = await argon2.hash(password)

    //Update user with new password

    await getConnection()
        .createQueryBuilder()
        .update(User)
        .set({ password: newPassword })
        .where("email = :email", { email })
        .execute()

    return res.status(201).json({ message: "Password succesfully changed. Please login with your new password" })
}