import { Request, Response } from "express"
import { ResetToken } from "../../entity/ResetToken"
import { getConnection } from "typeorm"
import dayjs from "dayjs"

export const verificationToken = async (req: Request, res: Response) => {
    
    const { email, token } = req.query

    var date = dayjs()

    //Delete Expired Token
    await getConnection()
        .createQueryBuilder()
        .delete()
        .from(ResetToken)
        .where("expire_at < :date", { date }).orWhere("used = TRUE")
        .execute()

    var record = await ResetToken.findOne({where: {
        email,
        token,
        used: false
    }})

    if(!record) return res.status(401).json({message: "The token has expired or does not exists"})

    //Set token to expired for deletion
    await getConnection()
        .createQueryBuilder()
        .update(ResetToken)
        .set({ used: true })
        .where("email = :email", { email })
        .execute()
        

    return res.status(201).json({ message: "OK" })
}