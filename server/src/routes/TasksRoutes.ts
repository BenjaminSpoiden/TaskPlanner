import { Router } from "express"
import { Authorization } from "../middlewares/Authorization"
import { fetchTasks } from "../controllers/tasks"

const router = Router()

router.get("/tasks", Authorization, fetchTasks)

export default router