import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/view/club-list/club-list.component";
import {ClubDetailsComponent} from "./club/view/club-details/club-details.component";
import {ClubEditComponent} from "./club/view/club-edit/club-edit.component";
export const routes: Routes=  [
  {path: 'clubs', component: ClubListComponent},
  {path: 'clubs/:id', component: ClubDetailsComponent},
  {path: 'clubs/:id/edit', component: ClubEditComponent},

  {path: '', redirectTo: 'clubs', pathMatch: 'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutes{}
