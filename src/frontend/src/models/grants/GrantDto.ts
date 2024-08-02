import CandidateDto from '@/models/candidates/CandidateDto'
import { ISOtoString } from '@/services/ConvertDateService'

export default class GrantDto {
    id?: string
    startDate!: string
    endDate!: string
    monthlyIncome?: number
    candidates?: CandidateDto[]
    vacancy?: number

    constructor(jsonObj: Partial<GrantDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.startDate = ISOtoString(jsonObj.startDate ?? null) 
            this.endDate = ISOtoString(jsonObj.endDate ?? null) 
            this.monthlyIncome = jsonObj.monthlyIncome
            this.candidates = jsonObj.candidates ? jsonObj.candidates.map(candidate => new CandidateDto(candidate)) : []
            this.vacancy = jsonObj.vacancy
        }
    }
}
