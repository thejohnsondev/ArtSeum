//
//  ArtDetailsViewModelWrapperImpl.swift
//  iosApp
//

import Foundation
import SwiftUI
import Shared

@MainActor
class ArtDetailsViewModelWrapperImpl: ArtDetailsViewModelProtocol, ObservableObject {
    
    private let viewModel: ArtDetailsViewModel

    @Published var state: ArtDetailsViewModel.State
    
    private var observationTask: Task<Void, Never>?
    
    init(artworkId: Int32) {
        self.viewModel = ViewModelFactory().makeArtDetailsViewModel()
        
        self.state = viewModel.state.value
        
        startObserving()
        
        self.perform(action: ArtDetailsViewModel.ActionLoadDetail(artworkId: artworkId))
    }
    
    deinit {
        observationTask?.cancel()
    }
    
    private func startObserving() {
        observationTask = Task { [weak self] in
            guard let self = self else { return }
            
            for await newState in self.viewModel.state {
                self.state = newState
            }
        }
    }
    
    func perform(action: ArtDetailsViewModel.Action) {
        viewModel.perform(action: action)
    }
}
