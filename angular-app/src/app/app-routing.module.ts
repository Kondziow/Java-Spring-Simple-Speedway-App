import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/club-list/club-list.component";
import {ClubEditComponent} from "./club/club-edit/club-edit.component";
import {ClubDetailsComponent} from "./club/club-details/club-details.component";
import {PlayerEditComponent} from "./player/player-edit/player-edit.component";


const routes: Routes = [
  {
    path: 'clubs',
    component: ClubListComponent,
    pathMatch: 'full'
  },
  {
    path: 'clubs/new',
    component: ClubEditComponent,
    pathMatch: 'full'
  },
  {
    path: 'clubs/:id',
    component: ClubDetailsComponent,
    pathMatch: 'full'
  },
  {
    path: 'clubs/:id/edit',
    component: ClubEditComponent,
    pathMatch: 'full'
  },
  {
    path: 'clubs/:id/newPlayer',
    component: PlayerEditComponent,
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
