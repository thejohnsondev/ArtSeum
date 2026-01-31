//
//  ArtListScreen.swift
//  iosApp
//
//  Created by Andrew on 24.01.2026.
//

import Foundation
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
                Color.artSeumBackground.ignoresSafeArea()
                if viewModel.state.isSearching {
                    searchContent
                } else {
                    browseContent
                }
            }
            .navigationTitle("Artworks")
            .searchable(text: viewModel.searchQueryBinding, prompt: "Search Artworks")
            .navigationDestination(for: Int32.self) { artworkId in
                ArtDetailsScreen(viewModel: ArtDetailsViewModelWrapperImpl(artworkId: artworkId))
            }
            .alert("Error", isPresented: Binding(
                get: { viewModel.state.error != nil },
                set: { _ in viewModel.perform(action: ArtListViewModel.ActionDismissError()) }
            )) {
                Button("Done") {
                    viewModel.perform(action: ArtListViewModel.ActionDismissError())
                }
            } message: {
                Text(viewModel.state.error?.message ?? "")
            }
        }
        .preferredColorScheme(.dark)
    }
    
    @ViewBuilder
    var searchContent: some View {
        let result = viewModel.state.searchResult
        
        switch result {
        case is SearchResult.Idle:
            EmptyView()
            
        case is SearchResult.Loading:
            ProgressView().tint(.artSeumAccent)
            
        case is SearchResult.Empty:
            ContentUnavailableView("No Results", systemImage: "magnifyingglass", description: Text("No results found for \(viewModel.state.searchQuery)"))

        case is SearchResult.Error:
            VStack {
                ContentUnavailableView("Error", systemImage: "exclamationmark.triangle", description: Text("Could not load search results"))
                Button("Retry") {
                    viewModel.perform(action: ArtListViewModel.ActionRefresh())
                }
                .tint(.white)
            }
            
        case let success as SearchResult.Success:
            searchList(items: success.data)
            
        default:
            EmptyView()
        }
    }
    
    @ViewBuilder
    var browseContent: some View {
        switch viewModel.state.screenState {
        case is ScreenState.Loading:
            if viewModel.state.browseArtworks.isEmpty {
                ProgressView().tint(.artSeumAccent)
            } else {
                browseList
            }
            
        case is ScreenState.Error:
            if viewModel.state.browseArtworks.isEmpty {
                VStack {
                    ContentUnavailableView("Error", systemImage: "exclamationmark.triangle", description: Text("Could not load artworks"))
                    Button("Retry") {
                        viewModel.perform(action: ArtListViewModel.ActionRefresh())
                    }
                    .tint(.white)
                }
            } else {
                browseList
            }
            
        default:
            browseList
        }
    }
    
    var browseList: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(viewModel.state.browseArtworks, id: \.id) {
                    artwork in
                    NavigationLink(value: artwork.id) {
                        ArtDisplayView(artwork: artwork)
                            .padding(.horizontal, 4)
                    }
                    .buttonStyle(PlainButtonStyle())
                    .onAppear {
                        if artwork == viewModel.state.browseArtworks.last {
                            viewModel.perform(action: ArtListViewModel.ActionLoadNextPage())
                        }
                    }
                }
                
                if viewModel.state.screenState is ScreenState.Loading && !viewModel.state.browseArtworks.isEmpty {
                    loadingIndicator
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
    
    func searchList(items: [ArtworkSearchItem]) -> some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(items, id: \.id) {
                    item in
                    NavigationLink(value: item.id?.int32Value ?? 0) {
                        SearchItemView(item: item)
                    }
                    .buttonStyle(PlainButtonStyle())
                    .onAppear {
                        if item.id == items.last?.id {
                            viewModel.perform(action: ArtListViewModel.ActionLoadNextPage())
                        }
                    }
                    
                    Divider()
                        .background(Color.artSeumSeparator)
                        .padding(.leading, 48)
                }
                
                if viewModel.state.screenState is ScreenState.Loading {
                   loadingIndicator
                }
            }
            .padding(.bottom, 20)
        }
        .scrollIndicators(.hidden)
    }
    
    var loadingIndicator: some View {
        HStack {
            Spacer()
            ProgressView().tint(.artSeumAccent)
            Spacer()
        }
        .padding(.vertical, 16)
    }
}

#Preview {
    ArtListScreen(viewModel: DemoArtListViewModelWrapper())
}
