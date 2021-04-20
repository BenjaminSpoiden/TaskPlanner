import { Request, Response } from "express"
import { Task } from "../../entity/Task"

export const addTask = async(req: Request, res: Response) => {

    //@ts-ignore
    const id = req.user.id

    const { title, description, priority } = req.body as Partial<Task>


    try {
        const addTask = await Task.create({
            title,
            description,
            priority,
            creatorId: id
        }).save()

        return res.status(201).json(addTask)

    }catch(e) {
        return res.status(403).json(e.message)        
    }
}