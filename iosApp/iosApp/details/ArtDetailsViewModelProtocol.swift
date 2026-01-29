//
//  ArtDetailsViewModelProtocol.swift
//  iosApp
//

import Foundation
import Shared
import SwiftUI

@MainActor
protocol ArtDetailsViewModelProtocol: ObservableObject {
    var state: ArtDetailsViewModel.State { get }
    func perform(action: ArtDetailsViewModel.Action)
}
