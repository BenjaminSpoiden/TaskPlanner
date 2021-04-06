import jwt from "jsonwebtoken"

export const generateToken = (userId: string) => {
   
    const payload = {
        id: userId
    }

    //@ts-ignore
    return jwt.sign(payload, process.env.ACCESS_TOKEN_SECRET, {expiresIn: 365 * 24 * 60 * 60 })
}