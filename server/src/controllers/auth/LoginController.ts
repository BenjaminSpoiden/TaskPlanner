import { Request, Response } from "express"
import { generateToken } from "../../utils/generateToken"
import { User } from "../../entity/User"
import { UserResponse } from "../../model/UserResponse"

export const login = async (req: Request, res: Response): Promise<Response<UserResponse>> => {

    const { email, password } = req.body

    try {
       const user = await User.onLogin(email, password)
       if(!user.user) {

        return res.status(401).json({...user})
       }

       const accessToken = generateToken(user.user.id)
       return res.status(201).json({
           user: accessToken,
           error: null
       })

    } catch(err) {
        return res.status(500).json({
            user: null,
            error: err.message
        })
    }
}