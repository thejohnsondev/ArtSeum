//
//  BaseViewModelWrapper.swift
//  iosApp
//
//  Created by Andrew on 31.01.2026.
//

import Foundation
import SwiftUI
import Shared

@MainActor
open class BaseViewModelWrapper<State>: ObservableObject {
    
    @Published var state: State
    
    private var observationTask: Task<Void, Never>?
    
    init(initialState: State) {
        self.state = initialState
    }
    
    deinit {
        observationTask?.cancel()
    }
    
    func startObserving<S: AsyncSequence>(_ sequence: S) where S.Element == State {
        observationTask = Task { [weak self] in
            do {
                for try await value in sequence {
                    self?.state = value
                }
            } catch {
                print("Error observing state: \(error)")
            }
        }
    }
}
