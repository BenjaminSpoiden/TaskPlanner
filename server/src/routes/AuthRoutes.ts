import { Router } from "express"
import { Authorization } from "../middlewares/Authorization"
import { currentUser, login, register } from "../controllers/auth"

const router = Router()

router.post("/login", login)
router.post("/register", register)
router.get("/currentUser", Authorization, currentUser)

export default router