import { Router } from "express"
import { Authorization } from "../middlewares/Authorization"
import { currentUser, forgotPassword, login, register, resetPassword, verificationToken } from "../controllers/auth"

const router = Router()

router.post("/login", login)
router.post("/register", register)
router.get("/currentUser", Authorization, currentUser)
router.post("/forgot-password", forgotPassword)
router.post("/reset-password", resetPassword)
router.get("/verification-token", verificationToken)

export default router