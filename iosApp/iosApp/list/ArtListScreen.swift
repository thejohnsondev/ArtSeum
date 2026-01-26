//
//  ArtListScreen.swift
//  iosApp
//
//  Created by Andrew on 24.01.2026.
//

import Foundation
import SwiftUI
import Shared

import SwiftUI
import Shared

struct ArtListScreen<VM: ArtListViewModelProtocol>: View {
    
    @StateObject var viewModel: VM
    
    init(viewModel: VM) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        NavigationStack {
            ZStack {
                Color.black.ignoresSafeArea()
                switch viewModel.state.screenState {
                case is ScreenState.Loading:
                    if viewModel.state.displayedArtworks.isEmpty {
                        ProgressView()
                            .tint(.white)
                    } else {
                        artworkList
                    }
                    
                case is ScreenState.Error:
                    if viewModel.state.displayedArtworks.isEmpty {
                        ContentUnavailableView("Error", systemImage: "exclamationmark.triangle", description: Text("Could not load artworks"))
                        Button("Retry") {
                            viewModel.perform(action: ArtListViewModel.ActionRefresh())
                        }
                        .tint(.white)
                    } else {
                        artworkList
                    }
                    
                default:
                    artworkList
                }
            }
            .navigationTitle("Artworks")
            .searchable(text: viewModel.searchQueryBinding, prompt: "Search Artworks")
            .onAppear {
                viewModel.perform(action: ArtListViewModel.ActionLoadData())
            }
        }
        .preferredColorScheme(.dark)
    }
    
    var artworkList: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(viewModel.state.displayedArtworks, id: \.id) { artwork in
                    ArtDisplayView(artwork: artwork)
                        .padding(.horizontal, 4)
                        .onAppear {
                            if artwork == viewModel.state.displayedArtworks.last {
                                viewModel.perform(action: ArtListViewModel.ActionLoadNextPage())
                            }
                        }
                }
                
                if viewModel.state.screenState is ScreenState.Loading && !viewModel.state.displayedArtworks.isEmpty {
                    HStack {
                        Spacer()
                        ProgressView().tint(.white)
                        Spacer()
                    }
                    .padding(.vertical, 16)
                }
            }
            .padding(.top, 8)
            .padding(.bottom, 20)
        }
        .scrollIndicators(.hidden)
        .refreshable {
            viewModel.perform(action: ArtListViewModel.ActionRefresh())
        }
    }
}

#Preview {
    ArtListScreen(viewModel: DemoArtListViewModelWrapper())
}
