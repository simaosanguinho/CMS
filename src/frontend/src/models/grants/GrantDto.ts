import CandidateDto from '@/models/candidates/CandidateDto'

export default class GrantDto {
    id?: string
    startDate?: string
    endDate?: string
    monthlyIncome?: number
    candidates?: CandidateDto[]

    constructor(jsonObj: Partial<GrantDto>) {
        Object.assign(this, jsonObj)
    }
}