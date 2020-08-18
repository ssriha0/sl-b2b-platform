import { IOption } from './IOption';
export interface ISurveyDetailsResponse {
  surveyQuestionnaireDetail: {
    buyerDetails: {
      logo: string,
      name: string
    },
    csat: {
      options: IOption[],
      questions: {
        aboveAverage: string,
        belowAverage: string,
        general: string
      }
    },
    nps: {
      question: string
    },
    surveyType: string
  };
}
