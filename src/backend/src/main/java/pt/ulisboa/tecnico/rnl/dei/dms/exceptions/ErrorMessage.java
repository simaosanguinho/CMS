package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    CANDIDATE_EMAIL_ALREADY_EXISTS("Existe um candidato registado com esse email."),
    CANDIDATE_IST_ID_ALREADY_EXISTS("Existe um candidato registado com esse IST ID."),
    CANDIDATE_NAME_CANNOT_BE_EMPTY("O candidato deve ter um nome."),
    CANDIDATE_EMAIL_CANNOT_BE_EMPTY("O candidato deve ter um email."),
    CANDIDATE_EMAIL_IS_INVALID("O email do candidato é inválido."),
    CANDIDATE_IST_ID_CANNOT_BE_EMPTY("O candidato deve ter um IST ID."),
    CANDIDATE_NOT_FOUND("Candidato não encontrado."),
    GRANT_START_DATE_CANNOT_BE_EMPTY("A bolsa deve ter uma data de início."),
    GRANT_END_DATE_CANNOT_BE_EMPTY("A bolsa deve ter uma data de fim."),
    GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE("O valor mensal da bolsa não pode ser negativo."),
    GRANT_MONTHLY_INCOME_CANNOT_BE_EMPTY("A bolsa deve ter um valor mensal."),
    GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE("A data de início não pode ser depois da data de fim."),
    GRANT_END_DATE_CANNOT_BE_IN_THE_PAST("A data de fim não pode ser no passado."),
    GRANT_HAS_NO_ENROLLMENTS("A bolsa não tem inscrições."),
    GRANT_IS_ALREADY_CLOSED("A bolsa já está fechada."),
    GRANT_HAS_NON_EVALUATED_ENROLLMENTS("A bolsa tem inscrições não avaliadas."),
    GRANT_NOT_FOUND("Bolsa não encontrada."),
    GRANT_VACANCY_CANNOT_BE_EMPTY("A bolsa deve ter vagas."),
    GRANT_VACANCY_CANNOT_BE_NEGATIVE("O número de vagas não pode ser negativo."),
    CATEGORIES_WEIGHT_MUST_SUM_TO_ONE("Os pesos das categorias devem somar 1."),
    ENROLLMENT_CANDIDATE_CANNOT_BE_EMPTY("A inscrição deve ter um candidato."),
    ENROLLMENT_GRANT_CANNOT_BE_EMPTY("A inscrição deve ter uma bolsa."),
    ENROLLMENT_CANNOT_BE_EMPTY("A inscrição não pode ser vazia."),
    ENROLLMENT_NOT_FOUND("Inscrição não encontrada."),
    EVALUATION_PARAMETERS_CANNOT_BE_EMPTY("A avaliação deve ter parâmetros."),
    EVALUATION_PARAMETERS_OUT_OF_BOUNDS("Os parâmetros da avaliação devem estar entre 0 e 20."),
    EVALUATION_NOT_FOUND("Avaliação não encontrada."),
    EVALUATION_ENROLLMENT_CANNOT_BE_EMPTY("A avaliação deve ter uma inscrição."),
    EVALUATION_CANNOT_BE_EMPTY("A avaliação não pode ser vazia."),
    ENROLLMENT_FINAL_SCORE_INVALID("A nota final da inscrição é inválida."),

    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}