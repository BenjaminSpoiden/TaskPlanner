import { Router } from "express"
import { Authorization } from "../middlewares/Authorization"
import { fetchTasks } from "../controllers/tasks"
import { addTask } from "../controllers/tasks/AddTask"

const router = Router()

router.get("/tasks", Authorization, fetchTasks)
router.post("/add-task", Authorization, addTask)

export default router