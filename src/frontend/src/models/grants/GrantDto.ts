import CandidateDto from '@/models/candidates/CandidateDto'
import { ISOtoString } from '@/services/ConvertDateService'

export default class GrantDto {
    id?: string
    startDate!: string
    endDate!: string
    monthlyIncome?: number
    candidates?: CandidateDto[]
    curricularEvaluationWeight?: number
    interviewWeight?: number
    practicalExerciseWeight?: number
    vacancy?: number
    isOngoing?: boolean

    constructor(jsonObj: Partial<GrantDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.startDate = ISOtoString(jsonObj.startDate ?? null) 
            this.endDate = ISOtoString(jsonObj.endDate ?? null) 
            this.monthlyIncome = jsonObj.monthlyIncome
            this.candidates = jsonObj.candidates ? jsonObj.candidates.map(candidate => new CandidateDto(candidate)) : []
            this.curricularEvaluationWeight = jsonObj.curricularEvaluationWeight
            this.interviewWeight = jsonObj.interviewWeight
            this.practicalExerciseWeight = jsonObj.practicalExerciseWeight
            this.vacancy = jsonObj.vacancy
            this.isOngoing = jsonObj.isOngoing
        }
    }
}
