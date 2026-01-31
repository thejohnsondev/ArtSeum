//
//  ArtDetailsViewModelWrapperImpl.swift
//  iosApp
//

import Foundation
import SwiftUI
import Shared

@MainActor
class ArtDetailsViewModelWrapperImpl: BaseViewModelWrapper<ArtDetailsViewModel.State>, ArtDetailsViewModelProtocol {
    
    private let viewModel: ArtDetailsViewModel
    
    init(artworkId: Int32) {
        self.viewModel = ViewModelFactory().makeArtDetailsViewModel()
        super.init(initialState: viewModel.state.value)
        startObserving(viewModel.state)
        
        self.perform(action: ArtDetailsViewModel.ActionLoadDetail(artworkId: artworkId))
    }
    
    func perform(action: ArtDetailsViewModel.Action) {
        viewModel.perform(action: action)
    }
}
