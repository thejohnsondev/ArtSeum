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
class ArtListViewModelWrapperImpl: BaseViewModelWrapper<ArtListViewModel.State>, ArtListViewModelProtocol {
    
    private let viewModel: ArtListViewModel
    
    init() {
        self.viewModel = ViewModelFactory().makeArtListViewModel()
        super.init(initialState: viewModel.state.value)
        startObserving(viewModel.state)
    }
    
    func perform(action: ArtListViewModel.Action) {
        viewModel.perform(action: action)
    }

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
