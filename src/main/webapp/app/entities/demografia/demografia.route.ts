import { Route } from '@angular/router';

import { DemografiaComponent } from './demografia.component';

export const DEMOGRAFIA_ROUTE: Route = {
  path: '',
  component: DemografiaComponent,
  data: {
    authorities: [],
    pageTitle: 'demografia'
  }
};
