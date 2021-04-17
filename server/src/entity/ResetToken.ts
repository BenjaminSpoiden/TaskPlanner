import { BaseEntity, Column, CreateDateColumn, Entity, PrimaryGeneratedColumn, UpdateDateColumn } from "typeorm";


@Entity("reset_token")
export class ResetToken extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    email: string

    @Column()
    token: number

    @Column({name: "expire_at"})
    expireAt: Date

    @CreateDateColumn({name: "created_at"})
    createdAt: Date

    @UpdateDateColumn({name: "updated_at"})
    updatedAt: Date

    @Column({default: false})
    used: boolean 
}