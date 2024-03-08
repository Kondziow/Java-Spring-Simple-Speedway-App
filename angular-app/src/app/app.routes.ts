import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/view/club-list/club-list.component";
export const routes: Routes=  [
  {path: 'api/v1/clubs', component: ClubListComponent},

  { path: '', redirectTo: 'api/v1/clubs', pathMatch: 'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutes{}
