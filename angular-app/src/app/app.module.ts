import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/view/club-list/club-list.component";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutes} from "./app.routes";
import {ClubService} from "./club/service/club.service";

@NgModule({
  declarations: [
    AppComponent,
    ClubListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutes
  ],
  providers: [
    ClubService
  ],
  bootstrap: [AppComponent]
})
export class AppModule{}
