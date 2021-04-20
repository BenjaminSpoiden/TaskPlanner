import { Request, Response } from "express"
import { getConnection } from "typeorm"
import argon2 from "argon2"
import { User } from "../../entity/User"
import { ResetToken } from "../../entity/ResetToken"


export const resetPassword = async(req: Request, res: Response) => {

    const tokenHeader = req.headers["verification-token"]
    const token = tokenHeader && tokenHeader.toString()

    if(!token) return res.status(401).json({ message: "Token Header was not provided or is incorrect." })

    const resetToken = await ResetToken.findOne({ token })
    if(!resetToken) return res.status(401).json({ message: "Token was not provided or has expired." })

    const { email } = req.query
    const { password, confirmPassword } = req.body

    if(password !== confirmPassword) return res.status(403).json({ message: "The passwords did not match." })
    if(password.length < 8) return res.status(403).json({ message: "The password must be at least 8 characters." })

    
    const newPassword = await argon2.hash(password)

    try {
        //Update user with new password
        await getConnection()
            .createQueryBuilder()
            .update(User)
            .set({ password: newPassword })
            .where("email = :email", { email })
            .execute()
    } catch(e) {
        return res.status(403).json({ message: e.message })
    }

    await getConnection()
        .createQueryBuilder()
        .delete()
        .from(ResetToken)
        .where("token = :token", { token })
        .execute()

    return res.status(201).json({ message: "Password succesfully changed. Please login with your new password." })
}