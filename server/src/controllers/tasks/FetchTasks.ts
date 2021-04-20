import { Request, Response } from "express"
import { getConnection } from "typeorm"

export const fetchTasks = async(req: Request, res: Response) => {
   
    //@ts-ignore
    const id = req.user.id

    //Find tasks based on userID
    try {

        const queryTasks = await getConnection().query(`
            SELECT task.*, u.name, u.surname FROM task
            LEFT JOIN public.user AS u
            ON u.id = task.creator_id
        `)

        return res.status(201).json({
            tasks: queryTasks,
            error: null
        })
    } catch (e) {
        return res.status(404).json({
            tasks: null,
            error: e.message
        })
    }
}