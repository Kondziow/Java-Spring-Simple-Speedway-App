import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/club-list/club-list.component";
import {ClubEditComponent} from "./club/club-edit/club-edit.component";


const routes: Routes = [
  {
    path: 'clubs',
    component: ClubListComponent,
    pathMatch: 'full'
  },
  {
    path: 'clubs/:id/edit',
    component: ClubEditComponent,
    pathMatch: 'full'
  },
  {
    path: '',
    redirectTo: 'clubs', pathMatch: 'full'
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
