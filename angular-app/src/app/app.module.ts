import {NgModule} from "@angular/core";
import {ClubListComponent} from "./club/view/club-list/club-list.component";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutes} from "./app.routes";
import {ClubService} from "./club/service/club.service";
import {HttpClientModule} from "@angular/common/http";
import {ClubDetailsComponent} from "./club/view/club-details/club-details.component";
import {ClubEditComponent} from "./club/view/club-edit/club-edit.component";

@NgModule({
  declarations: [
    AppComponent,
    ClubListComponent,
    ClubDetailsComponent,
    ClubEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutes,
    HttpClientModule
  ],
  providers: [
    ClubService
  ],
  bootstrap: [AppComponent]
})
export class AppModule{}
