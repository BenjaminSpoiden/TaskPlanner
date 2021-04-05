import express from "express"
import dotenv from "dotenv"
dotenv.config()
import { createConnection } from "typeorm"

const main = async () => {

    const app = express()
    const PORT = process.env.PORT

    app.use(express.json())
    app.use(express.urlencoded({extended: true}))

    await createConnection()

    app.get("/", (_, res) => {
        res.send({response: "Hello Server"})
    })

    app.listen(PORT, () => {
        console.log(`Server Ready at http://localhost:${PORT}`)
    })

}

main().catch(e => console.log("Error: ", e))