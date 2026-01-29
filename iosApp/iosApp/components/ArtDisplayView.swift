//
//  ArtDisplayView.swift
//  iosApp
//
//  Created by Andrew on 26.01.2026.
//

import SwiftUI
import Shared

struct ArtDisplayView: View {
    let artwork: Artwork
    var cornerRadius: CGFloat = 20
    var shadowRadius: CGFloat = 4
    
    @State private var currentPage = 0
    
    private var lqipImage: UIImage? {
        artwork.thumbnail?.lqip.base64ToImage()
    }
    
    private var imagesUrls: [String] {
        return artwork.imagesUrls ?? []
    }
    
    var body: some View {
        ZStack(alignment: .bottomLeading) {
            if imagesUrls.count > 1 {
                TabView(selection: $currentPage) {
                    ForEach(0..<imagesUrls.count, id: \.self) { index in
                        LoadedImage(url: imagesUrls[index], placeholder: lqipImage)
                            .tag(index)
                    }
                }
                .tabViewStyle(.page(indexDisplayMode: .never))
            } else {
                LoadedImage(url: imagesUrls.first, placeholder: lqipImage)
            }
            
            LinearGradient(
                colors: [.clear, .black.opacity(0.8)],
                startPoint: UnitPoint(x: 0.5, y: 0.4),
                endPoint: .bottom
            )
            .allowsHitTesting(false)
            
            VStack(alignment: .leading, spacing: 0) {
                if imagesUrls.count > 1 {
                    HStack(spacing: 6) {
                        Spacer()
                        ForEach(0..<imagesUrls.count, id: \.self) { index in
                            Circle()
                                .fill(Color.white)
                                .opacity(currentPage == index ? 1.0 : 0.5)
                                .frame(width: 6, height: 6)
                        }
                        Spacer()
                    }
                    .padding(.bottom, 12)
                }
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(artwork.title)
                        .font(.title3)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .lineLimit(1)
                    
                    Text(artwork.artist)
                        .font(.subheadline)
                        .foregroundColor(.white)
                        .lineLimit(1)
                }
            }
            .padding(16)
        }
        .aspectRatio(1, contentMode: .fit)
        .background(Color(.systemBackground))
        .clipShape(RoundedRectangle(cornerRadius: cornerRadius))
        .shadow(radius: shadowRadius)
    }
}


struct PlaceholderView: View {
    let image: UIImage?
    
    var body: some View {
        if let uiImage = image {
            Image(uiImage: uiImage)
                .resizable()
                .scaledToFill()
        } else {
            Color.gray.opacity(0.3)
        }
    }
}

#Preview {
    ArtDisplayView(artwork: Artwork.companion.demo)
        .padding()
        .frame(width: 350)
}
