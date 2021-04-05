import express from "express"

const main = async () => {

    const app = express()

    app.get("/", (_, res) => {
        res.send("Hello Server")
    })

    app.listen(4000, () => {
        console.log(`Server Ready at http://localhost:4000`)
    })

}

main().catch(e => console.log("Error: ", e))