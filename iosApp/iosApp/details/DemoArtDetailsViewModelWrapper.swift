//
//  DemoArtDetailsViewModelWrapper.swift
//  iosApp
//

import Foundation
import SwiftUI
import Shared

@MainActor
class DemoArtDetailsViewModelWrapper: ArtDetailsViewModelProtocol {
    
    @Published var state: ArtDetailsViewModel.State
    
    init(state: ArtDetailsViewModel.State = .init(
        screenState: ScreenState.ShowContent(),
        artwork: Artwork.companion.demo,
        selectedImageIndex: 0
    )) {
        self.state = state
    }
    
    func perform(action: ArtDetailsViewModel.Action) {
        print("Demo Wrapper performed: \(action)")
        
        if let swipeAction = action as? ArtDetailsViewModel.ActionImageSwiped {
            self.state = ArtDetailsViewModel.State(
                screenState: self.state.screenState,
                artwork: self.state.artwork,
                selectedImageIndex: swipeAction.index
            )
        }
    }
}
