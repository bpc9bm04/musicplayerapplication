import { NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule} from '@angular/material/snack-bar'
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { PlaylistComponent } from './components/playlist/playlist.component';
import { DashboardContainerComponent } from './components/dashboard-container/dashboard-container.component';
import { PlaylistContainerComponent } from './components/playlist-container/playlist-container.component';
import { PlaylistDialogComponent } from './components/playlist-dialog/playlist-dialog.component';
import { TrackContainerComponent } from './components/track-container/track-container.component';
import { ThumbnailComponent } from './components/thumbnail/thumbnail.component';
import { BookmarkComponent } from './components/bookmark/bookmark.component';
import { SearchComponent } from './components/search/search.component';
import { MuzixRouterModule } from './muzix-router.module';
import { TrackService } from './track.service';
import { PlaylistService } from './playlist.service'
import { HttpHandlerService } from './httphandler.service';
import { TokenInterceptor } from './interceptor.service';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';


/* An export what you put is the exports property of the @NgModule decorator.
It enables an angular module to expose some of its components/directives/ pipes to the other modules in the application.
Without it the, the components, directives, pipes defined in the module could only be used in that module
*/

@NgModule({
  imports: [CommonModule, FormsModule, HttpClientModule, MuzixRouterModule, MatCardModule, MatButtonModule, MatSnackBarModule, MatDialogModule, MatInputModule, MatIconModule],
  exports: [ThumbnailComponent, TrackContainerComponent, MuzixRouterModule, PlaylistComponent, DashboardContainerComponent,
             PlaylistContainerComponent, PlaylistDialogComponent, BookmarkComponent, SearchComponent],
  declarations: [ThumbnailComponent, TrackContainerComponent, PlaylistComponent, DashboardContainerComponent,
                 SearchComponent, PlaylistContainerComponent, PlaylistDialogComponent, BookmarkComponent],
  entryComponents: [PlaylistDialogComponent],
  providers: [TrackService, PlaylistService, HttpHandlerService, {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}], 
 
})
export class MuzixModule { }
