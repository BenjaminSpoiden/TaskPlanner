import { Priority } from "../model/Priority";
import { BaseEntity, Column, CreateDateColumn, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn, UpdateDateColumn } from "typeorm";
import { User } from "./User";


@Entity("task")
export class Task extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    title: string

    @Column({name: "is_completed", default: false})
    isCompleted: boolean

    @Column()
    description: string

    @Column({
        type: "enum",
        enum: Priority
    })
    priority: Priority

    @ManyToOne(() => User, user => user.tasks)
    @JoinColumn({ name: "creator_id" })
    creator: User

    @Column({name: "creator_id"})
    creatorId: string

    @CreateDateColumn({name: "created_at"})
    createdAt: Date

    @UpdateDateColumn({ name: "updated_at"})
    updatedAt: Date
}