
export default class EnrollmentDto {
    id?: string
    candidateId?: string    
    grantId?: string
    isEvaluated?: boolean
    finalScore?: number

    constructor(jsonObj: Partial<EnrollmentDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.candidateId = jsonObj.candidateId
            this.grantId = jsonObj.grantId
            this.isEvaluated = jsonObj.isEvaluated
            this.finalScore = jsonObj.finalScore
        }
    }
}