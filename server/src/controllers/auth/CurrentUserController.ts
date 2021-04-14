import { Request, Response } from "express"
import { User } from "../../entity/User"


export const currentUser = async (req: Request, res: Response) => {

    //@ts-ignore
    const id = req.user.id

    const user = await User.findOne({ id })
    if(!user) {
        return res.status(404).json({
            user: null,
            error: "Could not find user."
        })
    } else {
        return res.status(201).json({
            user, 
            error: null
        })
    }
}