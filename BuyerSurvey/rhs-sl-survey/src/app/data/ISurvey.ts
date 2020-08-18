export interface ISurvey {
  surveyRequest: {
    key: string;
    // surveyType: string;
    CSAT: {
      rating: number,
      options: {
        optionID: Array<number>
      },
      comments: string
    };
    NPS: { rating: number, comments: string };
    agreed: boolean;
    submit: boolean;
  };
}
