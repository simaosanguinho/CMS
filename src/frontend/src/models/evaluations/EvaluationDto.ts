export default class EvaluationDto {
    id?: string
    scores?: number[]

    constructor(jsonObj: Partial<EvaluationDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.scores = jsonObj.scores
        }
    }
}