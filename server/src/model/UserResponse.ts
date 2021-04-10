import { User } from "../entity/User";

export interface UserResponse {
    user?: User | null
    error?: any
}