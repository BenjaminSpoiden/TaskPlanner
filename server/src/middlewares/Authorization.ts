import { Request, Response, NextFunction } from "express"
import jwt from "jsonwebtoken"

export const Authorization = (req: Request, res: Response, next: NextFunction) => {

    const authHeader = req.headers["authorization"]
    const accessToken = authHeader && authHeader.split(' ')[1] // Bearer TOKEN

    if(!accessToken) return res.status(403).json({ message: "You are not authorized." })

    try {
        //@ts-ignore
        const user = jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET)

        //@ts-ignore
        req.user = user
        next()
    }catch(e) {
        return res.status(403).json({ message: "The token is not valid." })
    }
    

}