export default class EvaluationDto {
    id?: string
    currricularEvaluation?: number
    interview?: number
    practicalExercise?: number

    constructor(jsonObj: Partial<EvaluationDto>) {
        if (jsonObj) {
            this.id = jsonObj.id
            this.currricularEvaluation = jsonObj.currricularEvaluation
            this.interview = jsonObj.interview
            this.practicalExercise = jsonObj.practicalExercise
        }
    }
}