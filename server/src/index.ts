import "reflect-metadata"
import express from "express"
import dotenv from "dotenv"
dotenv.config()
import { createConnection } from "typeorm"
import AuthRoutes from "./routes/AuthRoutes"
import TasksRoutes from './routes/TasksRoutes'
import { ResetToken } from "./entity/ResetToken"

const main = async () => {

    const app = express()
    const PORT = process.env.PORT


    app.use(express.json())
    app.use(express.urlencoded({extended: false}))

    await createConnection()
    
    app.get("/", (_, res) => {
        res.send({response: "Hello Server"})
    })

    app.use(AuthRoutes)
    app.use(TasksRoutes)

    app.listen(PORT, () => {
        console.log(`Server Ready at http://localhost:${PORT}`)
    })

}

main().catch(e => console.log("Error: ", e))