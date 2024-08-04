
export default class EnrollmentDto {
    id?: string
    candidateId?: string    
    grantId?: string
    isEvaluated?: boolean

    constructor(jsonObj: Partial<EnrollmentDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.candidateId = jsonObj.candidateId
            this.grantId = jsonObj.grantId
            this.isEvaluated = jsonObj.isEvaluated
        }
    }
}