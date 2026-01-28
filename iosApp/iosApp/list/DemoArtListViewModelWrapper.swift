//
//  DemoArtListViewModelWrapper.swift
//  iosApp
//
//  Created by Andrew on 26.01.2026.
//

import Foundation
import SwiftUI
import Shared

@MainActor
class DemoArtListViewModelWrapper: ArtListViewModelProtocol {
    
    @Published var state: ArtListViewModel.State
    
    private var localSearchQuery: String = ""
    
    init(state: ArtListViewModel.State = .init(
        screenState: ScreenState.ShowContent(),
        browseArtworks: [Artwork.companion.demo,],
        searchResult: SearchResult.Idle(),
        isSearching: false,
        searchQuery: "",
        currentPage: 1,
        searchPage: 1
    )) {
        self.state = state
        self.localSearchQuery = state.searchQuery
    }
    
    func perform(action: ArtListViewModel.Action) {
        print("Demo Wrapper performed: \(action)")

        if action is ArtListViewModel.ActionRefresh {
            // Simulate refresh loading logic if needed
        }
    }
    
    var searchQueryBinding: Binding<String> {
        Binding(
            get: { self.state.searchQuery },
            set: { query in
                // var newState = self.state // State is immutable in KMP usually, copy needed?
                // Using constructor for demo
                self.state = ArtListViewModel.State(
                    screenState: self.state.screenState,
                    browseArtworks: self.state.browseArtworks,
                    searchResult: self.state.searchResult,
                    isSearching: !query.isEmpty,
                    searchQuery: query,
                    currentPage: self.state.currentPage,
                    searchPage: self.state.searchPage
                )
            }
        )
    }
}
