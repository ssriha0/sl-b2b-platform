import { InMemoryDbService } from 'angular-in-memory-web-api';
import { IOption } from '../../data/IOption';

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const options: IOption[] = [
      { id: 1, text: 'Arrive on time' },
      { id: 2, text: 'Explain service' },
      { id: 3, text: 'Offer solutions' },
      { id: 4, text: 'Leave work area clean' },
      { id: 5, text: 'Complete service' },
      { id: 6, text: 'Act courteously' }
    ];

    const CSAT = {
      questions: {
        general: 'Please rate the Service Pro who took care of you today.',
        belowAverage: 'What could your Service Pro have done better?',
        aboveAverage: 'Did your Service Pro…'
      },
      options: options
    };

    const NPS = {
      question: 'On a scale of 0 to 10, How likely is that you would recommend [] to a friend, relative or colleague?'
    };

    const BUYER_INFO = {
      name: 'Nest - SL Managed Services', logo: 'https://via.placeholder.com/150x100.png?text=Logo'
    };

    return {
      getDetails: { surveyQuestionnaireDetail: { buyerDetails: BUYER_INFO, csat: CSAT, nps: NPS } },
      validate: { surveyValidation: { isSubmitted: false }}
    };
  }
}
