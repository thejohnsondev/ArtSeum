//
//  ArtListViewModelWrapper.swift
//  iosApp
//
//  Created by Andrew on 26.01.2026.
//

import Foundation
import Shared
import SwiftUI

@MainActor
protocol ArtListViewModelProtocol: ObservableObject {
    var state: ArtListViewModel.State { get }
    func perform(action: ArtListViewModel.Action)
    var searchQueryBinding: Binding<String> { get }
}
