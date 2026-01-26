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
        searchResults: [],
        isSearching: false,
        searchQuery: "",
        currentPage: 1
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
                var newState = self.state
                self.state = ArtListViewModel.State(
                    screenState: self.state.screenState,
                    browseArtworks: self.state.browseArtworks,
                    searchResults: self.state.searchResults,
                    isSearching: !query.isEmpty,
                    searchQuery: query,
                    currentPage: self.state.currentPage
                )
            }
        )
    }
}
