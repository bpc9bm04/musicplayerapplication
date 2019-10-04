import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Router } from '@angular/router/src/router';
import { TrackContainerComponent } from './components/track-container/track-container.component';
import { DashboardContainerComponent } from './components/dashboard-container/dashboard-container.component';
import { PlaylistContainerComponent } from './components/playlist-container/playlist-container.component';
import { BookmarkComponent } from './components/bookmark/bookmark.component';
import { PlaylistComponent } from './components/playlist/playlist.component';
import { SearchComponent } from './components/search/search.component';
import { AuthGuard } from '../../authguard.service';

const trackRoutes: Routes = [    
    {
        path: 'playlists',
        children:[
            {
                path: 'featured',
                component: PlaylistContainerComponent,
                canActivate: [AuthGuard],   
                data:{
                    playlistType: 'featured'
                },                
            }
        ]
    },
    {
        path: 'tracks',
        children: [ 
            {
                path: '',
                redirectTo: '/tracks/top',
                pathMatch: 'full',
                canActivate: [AuthGuard]
            },           
            {
                path: 'top',
                component: DashboardContainerComponent,
                canActivate: [AuthGuard],
                data:{
                    trackType: 'top'
                }
            },
            {
                path: 'playlist',
                component: PlaylistComponent,
                canActivate: [AuthGuard]                
            },
            {
                path: 'bookmarks',
                component: BookmarkComponent,
                canActivate: [AuthGuard]
            },            
            {
                path: 'search', 
                component: SearchComponent,
                canActivate: [AuthGuard]                            
            }
        ]
    }
]

@NgModule({
    imports: [
        RouterModule.forChild(trackRoutes)
    ],
    exports:[
        RouterModule
    ]
})

export class MuzixRouterModule{ }