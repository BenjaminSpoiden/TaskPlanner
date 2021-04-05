import { BaseEntity, BeforeInsert, Column, CreateDateColumn, Entity, PrimaryGeneratedColumn, UpdateDateColumn } from "typeorm";
import argon2 from "argon2"
import { IsEmail, IsNotEmpty, MinLength, validate } from "class-validator"
import { CreateUserInput } from "src/model/CreateUserInput";

@Entity("user")
export class User extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number

    @Column({unique: true})
    @IsEmail({}, {message: "Please enter your email."})
    @IsNotEmpty({message: "Please enter an email."})
    email: string

    @Column()
    @MinLength(8, {message: "The password must be atleast 8 characters."})
    @IsNotEmpty({message: "Please enter your password."})
    password: string

    @Column()
    @IsNotEmpty({message: "Please enter your first name."})
    name: string

    @Column()
    @IsNotEmpty({message: "Please enter your surname."})
    surname: string

    @CreateDateColumn({name: "created_at"})
    createdAt: Date

    @UpdateDateColumn({name: "updated_at"})
    updatedAt: Date

    
    @BeforeInsert()
    async hashPassword() {
        this.password = await argon2.hash(this.password)
    }

    static async onLogin(email: string, password: string) {
        const user = await User.findOne({ email })
        if(!user) throw new Error("No user found with this email.")

        const comparePassword = await argon2.verify(user.password, password)
        if(!comparePassword) throw new Error("The password did not match.")

        return user
    }

    static async onCreateUser({email, password, name, surname}: CreateUserInput) {
        const user = User.create({ email, password, name, surname })
        const errors = await validate(user)

        if(errors.length > 0) {
            return {...errors[0].constraints}
        }
        user.save().catch(e => {
            throw new Error(e)
        })
        return user
    }
}