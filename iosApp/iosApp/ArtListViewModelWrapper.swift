//
//  ArtListViewModelWrapper.swift
//  iosApp
//
//  Created by Andrew on 24.01.2026.
//

import Foundation
import SwiftUI
import Shared

@MainActor
class ArtListViewModelWrapper: ObservableObject {
    
    private let viewModel: ArtListViewModel

    @Published var state: ArtListViewModel.State
    
    private var observationTask: Task<Void, Never>?
    
    init() {
        self.viewModel = ViewModelFactory().makeArtListViewModel()
        
        self.state = viewModel.state.value
        
        startObserving()
    }
    
    deinit {
        observationTask?.cancel()
    }
    
    // MARK: - Observation Logic (SKIE Enabled)
    private func startObserving() {
        observationTask = Task { [weak self] in
            guard let self = self else { return }
            
            for await newState in self.viewModel.state {
                self.state = newState
            }
        }
    }
    
    // MARK: - Action Handling
    func perform(action: ArtListViewModel.Action) {
        viewModel.perform(action: action)
    }
    
    // MARK: - SwiftUI Helpers

    var searchQueryBinding: Binding<String> {
        Binding(
            get: { self.state.searchQuery },
            set: { query in
                if query.isEmpty {
                    self.perform(action: ArtListViewModel.ActionClearSearch())
                } else {
                    self.perform(action: ArtListViewModel.ActionSearch(query: query))
                }
            }
        )
    }
}
