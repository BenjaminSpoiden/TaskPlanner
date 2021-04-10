import { Request, Response } from "express";
import { User } from "../../entity/User";
import { generateToken } from "../../utils/generateToken";
import { validate } from "class-validator"
import { UserResponse } from "../../model/UserResponse"


export const register = async (req: Request, res: Response): Promise<Response<UserResponse>> => {
    const {email, name, surname, password} = req.body

    try {
        const user = User.create({ email, password, name, surname })
        const errors = await validate(user)

        if(errors.length > 0) {
            return res.status(401).json({
                user: null,
                error: {...errors[0].constraints}
            })
        }
        await user.save()

        const accessToken = generateToken(user.id)

        return res.status(201).json({ 
            user: accessToken,
            error: null 
        })

    } catch(e) {
        return res.status(500).send({
            user: null,
            error: e.message
        })
    }
}