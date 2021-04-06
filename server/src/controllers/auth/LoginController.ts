import { Request, Response } from "express"
import { generateToken } from "../../utils/generateToken"
import { User } from "../../entity/User"


export const login = async (req: Request, res: Response) => {

    const { email, password } = req.body

    try {
       const user = await User.onLogin(email, password)
       if(!user.data) {
        return res.status(401).json({...user})
       }

       const accessToken = generateToken(user.data.id)
       return res.status(201).json({ accessToken })

    } catch(err) {
        return res.status(500).send(err.message)
    }
}